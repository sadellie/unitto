/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023-2024 Elshan Agaev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.sadellie.unitto.data.backup

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.sadellie.unitto.data.database.DATABASE_NAME
import com.sadellie.unitto.data.database.UnittoDatabase
import com.sadellie.unitto.data.database.checkpoint
import com.sadellie.unitto.data.userprefs.USER_PREFERENCES
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

class BackupManager {
    suspend fun backup(
        context: Context,
        backupFileUri: Uri,
        database: UnittoDatabase,
    ) = withContext(Dispatchers.IO) {
        context
            .applicationContext
            .contentResolver
            .openOutputStream(backupFileUri)
            ?.use { backupFileOutputStream ->
                ZipOutputStream(backupFileOutputStream.buffered())
                    .use { zipOutputStream ->
                        // Datastore
                        context
                            .datastoreFile
                            .writeToZip(zipOutputStream, DATASTORE_FILE_NAME)

                        // Database
                        database.checkpoint()
                        database
                            .file
                            .writeToZip(zipOutputStream, DATABASE_FILE_NAME)
                    }
            }
    }

    suspend fun restore(
        context: Context,
        backupFileUri: Uri,
        database: UnittoDatabase,
    ) = withContext(Dispatchers.IO) {
        context
            .applicationContext
            .contentResolver
            .openInputStream(backupFileUri)
            ?.use { backupFileInputStream ->
                ZipInputStream(backupFileInputStream).use { zipInputStream ->
                    var entry = zipInputStream.nextEntry
                    while (entry != null) {
                        when (entry.name) {
                            DATASTORE_FILE_NAME -> {
                                context
                                    .datastoreFile
                                    .writeFromZip(zipInputStream)
                            }

                            DATABASE_FILE_NAME -> {
                                database.checkpoint()
                                database.close()
                                database
                                    .file
                                    .writeFromZip(zipInputStream)
                            }
                        }
                        entry = zipInputStream.nextEntry
                    }
                }
            } ?: return@withContext // Don't restart activity if the file is not found

        context.restartActivity()
    }
}

private val Context.datastoreFile: File
    get() = this
        .filesDir
        .addChild(DATASTORE_FOLDER_NAME)
        .addChild(DATASTORE_FILE_NAME)

private fun Context.restartActivity() {
    val componentName: ComponentName = this
        .packageManager
        .getLaunchIntentForPackage(this.packageName)
        ?.component
        ?: throw Exception("BackupManager was unable to find component for this context")

    val restartIntent: Intent = Intent.makeRestartActivityTask(componentName)
    this.startActivity(restartIntent)
    Runtime.getRuntime().exit(0)
}

private val UnittoDatabase.file: File
    get() = File(this.openHelper.writableDatabase.path!!) // Will be caught on higher level if null

/**
 * Will write content of this [File] into given [ZipOutputStream].
 *
 * @receiver Source [File].
 * @param zipOutputStream Target [ZipOutputStream].
 * @param name Name of the file ([ZipEntry]) that will be created in the archive [ZipOutputStream].
 */
private fun File.writeToZip(zipOutputStream: ZipOutputStream, name: String) = this
    .inputStream()
    .buffered()
    .use { inputStream ->
        // Using explicit names only to reduce overhead. Don't use this.name,
        zipOutputStream.putNextEntry(ZipEntry(name))
        inputStream.copyTo(zipOutputStream)
    }

/**
 * Will write content from given [ZipInputStream] into [File].
 *
 * @receiver Target [File] that will be filled with content from [ZipInputStream].
 * @param zipInputStream Source [ZipInputStream].
 */
private fun File.writeFromZip(zipInputStream: ZipInputStream) = this
    .outputStream()
    .buffered()
    .use { outputStream ->
        zipInputStream.copyTo(outputStream)
    }

private fun File.addChild(child: String): File = File(this, child)

private const val DATASTORE_FILE_NAME = "$USER_PREFERENCES.preferences_pb"
private const val DATASTORE_FOLDER_NAME = "datastore"
private const val DATABASE_FILE_NAME = "$DATABASE_NAME.db"
