# UI module

Custom UI components shared between `:feature` modules. Check for more custom UI components in `:feature` modules.

## Graph

```mermaid
%%{
  init: {
    'theme': 'dark'
  }
}%%

graph LR
  subgraph :core
    :core:ui["ui"]
    :core:common["common"]
    :core:navigation["navigation"]
    :core:designsystem["designsystem"]
  end
  subgraph :feature
    :feature:settings["settings"]
    :feature:bodymass["bodymass"]
    :feature:calculator["calculator"]
    :feature:converter["converter"]
    :feature:timezone["timezone"]
    :feature:datecalculator["datecalculator"]
    :feature:glance["glance"]
    :feature:programmer["programmer"]
  end
  :feature:settings --> :core:ui
  :sharedApp --> :core:ui
  :feature:bodymass --> :core:ui
  :feature:calculator --> :core:ui
  :feature:converter --> :core:ui
  :feature:timezone --> :core:ui
  :feature:datecalculator --> :core:ui
  :core:ui --> :core:common
  :core:ui --> :core:navigation
  :core:ui --> :core:designsystem
  :feature:glance --> :core:ui
  :feature:programmer --> :core:ui

classDef focus fill:#769566,stroke:#fff,stroke-width:2px,color:#fff;
class :core:ui focus
```