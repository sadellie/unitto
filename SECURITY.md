# ⚠ Security

## Permissions 

### `com.sadellie.unitto.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION`
Read (boring): https://developer.android.com/about/versions/14/behavior-changes-14#runtime-receivers-exported

### `android.permission.INTERNET`
Used in **Unit Converter** to update currency rates. Requests are made *only* when you select a currency unit. See: [CurrencyApiService.kt](https://github.com/sadellie/unitto/blob/2405a2656ac8de3877a647f19813b4498f24c7a8/data/units/src/main/java/com/sadellie/unitto/data/units/remote/CurrencyApiService.kt) and [UnitsRepository.kt](https://github.com/sadellie/unitto/blob/49f1520d88843ed3cc7ebc02307e877950c9899b/data/units/src/main/java/com/sadellie/unitto/data/units/UnitsRepository.kt)

### `android.permission.ACCESS_NETWORK_STATE`
Used in **Unit Converter** as a callback. Retries to update currency rates if there was an error (no network, for example) and the Internet connection is back. See: [NetworkUtils.kt](https://github.com/sadellie/unitto/blob/d7db2780c83cdda33335c5278cafe4148c5e7778/feature/converter/src/main/java/com/sadellie/unitto/feature/converter/NetworkUtils.kt) and [ConverterScreen.kt](https://github.com/sadellie/unitto/blob/6fcf340abac7d34d2de9b142bf8208b55a09079f/feature/converter/src/main/java/com/sadellie/unitto/feature/converter/ConverterScreen.kt)

### android.permission.WAKE_LOCK
Not used explicitly. Added automatically by Widget feature.

### android.permission.RECEIVE_BOOT_COMPLETED
Not used explicitly. Added automatically by Widget feature.

### android.permission.FOREGROUND_SERVICE
Not used explicitly. Added automatically by Widget feature.

## Network service (not FOSS)

The app uses [Free Currency Exchange Rates API](https://github.com/fawazahmed0/exchange-api) by [fawazahmed0](https://github.com/fawazahmed0).
Requests are send to `cdn.jsdelivr.net`.
