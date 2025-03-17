# Navigation module

App screen routes and shortcuts. Unitto uses type-safe navigation. This module doesn't have host.

## Graph

```mermaid
%%{
  init: {
    'theme': 'dark'
  }
}%%

graph LR
  subgraph :core
    :core:navigation["navigation"]
    :core:common["common"]
    :core:designsystem["designsystem"]
    :core:ui["ui"]
    :core:datastore["datastore"]
  end
  subgraph :feature
    :feature:converter["converter"]
    :feature:settings["settings"]
    :feature:datecalculator["datecalculator"]
    :feature:timezone["timezone"]
    :feature:graphing["graphing"]
    :feature:bodymass["bodymass"]
    :feature:calculator["calculator"]
  end
  :core:navigation --> :core:common
  :core:navigation --> :core:designsystem
  :feature:converter --> :core:navigation
  :feature:settings --> :core:navigation
  :app --> :core:navigation
  :feature:datecalculator --> :core:navigation
  :feature:timezone --> :core:navigation
  :core:ui --> :core:navigation
  :feature:graphing --> :core:navigation
  :core:datastore --> :core:navigation
  :feature:bodymass --> :core:navigation
  :feature:calculator --> :core:navigation

classDef focus fill:#769566,stroke:#fff,stroke-width:2px,color:#fff;
class :core:navigation focus
```