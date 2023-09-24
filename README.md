# Unitto – calculator and unit converter

<p align="middle">
    <img src="./fastlane/metadata/android/en-US/images/featureGraphic.png" width="99%" />
    <img src="./fastlane/metadata/android/en-US/images/phoneScreenshots/slide1.png" width="19%" />
    <img src="./fastlane/metadata/android/en-US/images/phoneScreenshots/slide2.png" width="19%" />
    <img src="./fastlane/metadata/android/en-US/images/phoneScreenshots/slide3.png" width="19%" />
    <img src="./fastlane/metadata/android/en-US/images/phoneScreenshots/slide4.png" width="19%" />
    <img src="./fastlane/metadata/android/en-US/images/phoneScreenshots/slide5.png" width="19%" />
</p>

## 📲 Download
<a href="https://play.google.com/store/apps/details?id=com.sadellie.unitto"><img alt="Google Play" src="./content/gplay.svg" height="80"/></a>
<a href="https://f-droid.org/packages/com.sadellie.unitto"><img alt="F-Droid" src="./content/fdroid.svg" height="80"/></a>
<a href="https://github.com/sadellie/unitto/releases/latest"><img alt="GitHub" src="./content/github.svg" height="80"/></a>

## 😎 Features
- **Instant** expression evaluation
- Expressions **history**
- **Copy**, **paste** and **cut** expression
- **Material You** Theme even for **old devices**
- **583** units and currencies
- **Smart** units search
- **Adaptive** units sorting algorithm
- **Small** app size
- **Bulk convert** units
- **Favorite** units
- Ability to **disable unit groups**
- Built-in **Date calculator**
- Customizable number **formatter**
- **SI Standard**

## ⚠ Security

### `com.sadellie.unitto.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION`
Read (boring): https://developer.android.com/about/versions/14/behavior-changes-14#runtime-receivers-exported

### `android.permission.INTERNET`
Used in **Unit Converter** to update currency rates. Requests are made *only* when you select a currency unit. See: [CurrencyApiService.kt](https://github.com/sadellie/unitto/blob/2405a2656ac8de3877a647f19813b4498f24c7a8/data/units/src/main/java/com/sadellie/unitto/data/units/remote/CurrencyApiService.kt) and [UnitsRepository.kt](https://github.com/sadellie/unitto/blob/49f1520d88843ed3cc7ebc02307e877950c9899b/data/units/src/main/java/com/sadellie/unitto/data/units/UnitsRepository.kt)

### `android.permission.ACCESS_NETWORK_STATE`
Used in **Unit Converter** as a callback. Retries to update currency rates if there was an error (no network, for example) and the Internet connection is back. See: [NetworkUtils.kt](https://github.com/sadellie/unitto/blob/d7db2780c83cdda33335c5278cafe4148c5e7778/feature/converter/src/main/java/com/sadellie/unitto/feature/converter/NetworkUtils.kt) and [ConverterScreen.kt](https://github.com/sadellie/unitto/blob/6fcf340abac7d34d2de9b142bf8208b55a09079f/feature/converter/src/main/java/com/sadellie/unitto/feature/converter/ConverterScreen.kt)

### Non-free network service
*Non-free* doesn't mean that you need to pay, put your credit card away. In this context it means that you can't host it on your machine (why the fuck would anyone want to host a currency API service?).

The app uses [Free Currency Rates API](https://github.com/fawazahmed0/currency-api) by [fawazahmed0](https://github.com/fawazahmed0).
Requests are send to `cdn.jsdelivr.net`.

<sup>TL;DR: the app is legit</sup>

## 👅 [Translate](https://poeditor.com/join/project/T4zjmoq8dx)
Join on **POEditor** to help.

## 💡 [Open issues](https://github.com/sadellie/unitto/issues/new)
Report bugs or request improvements. I may close your issue as not planned and reopen it later (things change).

## 🎤 [Start discussions](https://github.com/sadellie/unitto/discussions/new/choose)
If you think that your question will not fit in "Issues", start a discussion.

## 👩‍💻 ~~Contribute code~~
1. At the moment I do not need any help in code.
2. Hard forks and alterations of Unitto are **not** welcomed. Use a "Fork" button so that commits' author is not lost.

## 🔎 Additional
Terms and Conditions: https://sadellie.github.io/unitto/terms

Privacy Policy: https://sadellie.github.io/unitto/privacy
