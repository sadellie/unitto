# Feature modules

## Graph

```mermaid
%%{
  init: {
    'theme': 'dark'
  }
}%%

graph LR
  subgraph :core
    :core:common["common"]
    :core:ui["ui"]
    :core:navigation["navigation"]
    :core:designsystem["designsystem"]
    :core:datastore["datastore"]
    :core:evaluatto["evaluatto"]
    :core:data["data"]
    :core:model["model"]
    :core:database["database"]
    :core:backup["backup"]
    :core:themmo["themmo"]
    :core:licenses["licenses"]
  end
  subgraph :feature
    :feature:converter["converter"]
    :feature:settings["settings"]
    :feature:bodymass["bodymass"]
    :feature:calculator["calculator"]
    :feature:datecalculator["datecalculator"]
    :feature:glance["glance"]
    :feature:graphing["graphing"]
    :feature:timezone["timezone"]
  end
  :feature:converter --> :core:common
  :feature:converter --> :core:ui
  :feature:converter --> :core:navigation
  :feature:converter --> :core:designsystem
  :feature:converter --> :core:datastore
  :feature:converter --> :core:evaluatto
  :feature:converter --> :core:data
  :feature:converter --> :core:model
  :feature:settings --> :core:common
  :feature:settings --> :core:ui
  :feature:settings --> :core:navigation
  :feature:settings --> :core:designsystem
  :feature:settings --> :core:datastore
  :feature:settings --> :core:database
  :feature:settings --> :core:backup
  :feature:settings --> :core:model
  :feature:settings --> :core:themmo
  :feature:settings --> :core:licenses
  :app --> :feature:bodymass
  :app --> :feature:calculator
  :app --> :feature:converter
  :app --> :feature:datecalculator
  :app --> :feature:glance
  :app --> :feature:graphing
  :app --> :feature:timezone
  :app --> :feature:settings
  :feature:datecalculator --> :core:common
  :feature:datecalculator --> :core:ui
  :feature:datecalculator --> :core:navigation
  :feature:datecalculator --> :core:designsystem
  :feature:datecalculator --> :core:datastore
  :feature:datecalculator --> :core:data
  :feature:datecalculator --> :core:model
  :feature:timezone --> :core:common
  :feature:timezone --> :core:ui
  :feature:timezone --> :core:navigation
  :feature:timezone --> :core:designsystem
  :feature:timezone --> :core:datastore
  :feature:timezone --> :core:data
  :feature:timezone --> :core:model
  :feature:glance --> :core:common
  :feature:glance --> :core:ui
  :feature:glance --> :core:designsystem
  :feature:glance --> :core:datastore
  :feature:glance --> :core:evaluatto
  :feature:graphing --> :core:common
  :feature:graphing --> :core:ui
  :feature:graphing --> :core:navigation
  :feature:graphing --> :core:designsystem
  :feature:graphing --> :core:datastore
  :feature:graphing --> :core:evaluatto
  :feature:graphing --> :core:model
  :feature:bodymass --> :core:common
  :feature:bodymass --> :core:ui
  :feature:bodymass --> :core:navigation
  :feature:bodymass --> :core:designsystem
  :feature:bodymass --> :core:datastore
  :feature:calculator --> :core:common
  :feature:calculator --> :core:ui
  :feature:calculator --> :core:navigation
  :feature:calculator --> :core:designsystem
  :feature:calculator --> :core:datastore
  :feature:calculator --> :core:evaluatto
  :feature:calculator --> :core:data
  :feature:calculator --> :core:model

classDef focus fill:#769566,stroke:#fff,stroke-width:2px,color:#fff;
class :feature:converter focus
class :feature:settings focus
class :feature:bodymass focus
class :feature:calculator focus
class :feature:datecalculator focus
class :feature:glance focus
class :feature:graphing focus
class :feature:timezone focus
```
# Feature modules

Each tab in navigation drawer is a feature. This is specific for Unitto, you may modularize your app differently.