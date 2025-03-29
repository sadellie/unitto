# Introduction

- Main development branch with latest commits: `dev` (for PRs)
- This project is NOT beginner/students friendly
- Line separator: **CRLF**. Currently no plans for **LF**
- Any usage of "AI" is strictly forbidden
- Any usage of copyrighted materials without permission is strictly forbidden

Steps:

1. Read this file
2. Choose what you want to contribute
3. Open an issue (unl)
4. Wait for approval
5. Contribute

Contributions:
- [Translations](#translations)
- [Code contributions](#code-contributions)
- [Small changes](#small-changes)

Do NOT contribute:

- Branding material changes
- Malicious code, obviously

## Translations

- Follow [the official guide](https://developer.android.com/studio/write/translations-editor)
- Currently not using web-based translation tools due to some issues (will resolve later)
- Lines in translations are cleared when original text changes significantly to avoid inconsistency. You will NOT be notified
- Keep punctuation consistent. If the original sentence ends with a comma, you need to include it too

### Add new language

- Open issue
- Wait for approval
- Open PR

### Edit existing translation

- Open PR. No need to open issues

## Code contributions

- Always open a new issue and wait for approval
- Explore the codebase. Each module has a README file. For example, [app module](./app/README.md)
- Use ktfmt (Google internal) formatter
- Use detekt (optional)
- Write tests
- Write comments if necessary
- Avoid touching user data unless it's really necessary
- State external sources if you used any

## Small changes

Only open an issue, do NOT open PRs.
