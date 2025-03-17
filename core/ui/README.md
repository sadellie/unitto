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
    :feature:converter["converter"]
    :feature:settings["settings"]
    :feature:datecalculator["datecalculator"]
    :feature:timezone["timezone"]
    :feature:glance["glance"]
    :feature:graphing["graphing"]
    :feature:bodymass["bodymass"]
    :feature:calculator["calculator"]
  end
  :feature:converter --> :core:ui
  :feature:settings --> :core:ui
  :app --> :core:ui
  :feature:datecalculator --> :core:ui
  :feature:timezone --> :core:ui
  :core:ui --> :core:common
  :core:ui --> :core:navigation
  :core:ui --> :core:designsystem
  :feature:glance --> :core:ui
  :feature:graphing --> :core:ui
  :feature:bodymass --> :core:ui
  :feature:calculator --> :core:ui

classDef focus fill:#769566,stroke:#fff,stroke-width:2px,color:#fff;
class :core:ui focus
```