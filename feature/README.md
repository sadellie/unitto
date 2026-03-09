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
    :core:backup["backup"]
    :core:common["common"]
    :core:ui["ui"]
    :core:navigation["navigation"]
    :core:designsystem["designsystem"]
    :core:data["data"]
    :core:datastore["datastore"]
    :core:database["database"]
    :core:model["model"]
    :core:themmo["themmo"]
    :core:licenses["licenses"]
    :core:evaluatto["evaluatto"]
  end
  subgraph :feature
    :feature:settings["settings"]
    :feature:glance["glance"]
    :feature:calculator["calculator"]
    :feature:converter["converter"]
    :feature:bodymass["bodymass"]
    :feature:datecalculator["datecalculator"]
    :feature:timezone["timezone"]
    :feature:programmer["programmer"]
  end
  :feature:settings --> :core:backup
  :feature:settings --> :core:common
  :feature:settings --> :core:ui
  :feature:settings --> :core:navigation
  :feature:settings --> :core:designsystem
  :feature:settings --> :core:data
  :feature:settings --> :core:datastore
  :feature:settings --> :core:database
  :feature:settings --> :core:model
  :feature:settings --> :core:themmo
  :feature:settings --> :core:licenses
  :sharedApp --> :feature:glance
  :sharedApp --> :feature:calculator
  :sharedApp --> :feature:converter
  :sharedApp --> :feature:bodymass
  :sharedApp --> :feature:datecalculator
  :sharedApp --> :feature:timezone
  :sharedApp --> :feature:programmer
  :sharedApp --> :feature:settings
  :feature:bodymass --> :core:common
  :feature:bodymass --> :core:ui
  :feature:bodymass --> :core:navigation
  :feature:bodymass --> :core:designsystem
  :feature:bodymass --> :core:datastore
  :feature:calculator --> :core:designsystem
  :feature:calculator --> :core:common
  :feature:calculator --> :core:model
  :feature:calculator --> :core:ui
  :feature:calculator --> :core:navigation
  :feature:calculator --> :core:evaluatto
  :feature:calculator --> :core:datastore
  :feature:calculator --> :core:data
  :feature:converter --> :core:common
  :feature:converter --> :core:ui
  :feature:converter --> :core:navigation
  :feature:converter --> :core:designsystem
  :feature:converter --> :core:datastore
  :feature:converter --> :core:evaluatto
  :feature:converter --> :core:data
  :feature:converter --> :core:model
  :feature:timezone --> :core:common
  :feature:timezone --> :core:ui
  :feature:timezone --> :core:navigation
  :feature:timezone --> :core:designsystem
  :feature:timezone --> :core:datastore
  :feature:timezone --> :core:data
  :feature:timezone --> :core:model
  :feature:datecalculator --> :core:common
  :feature:datecalculator --> :core:ui
  :feature:datecalculator --> :core:navigation
  :feature:datecalculator --> :core:designsystem
  :feature:datecalculator --> :core:datastore
  :feature:datecalculator --> :core:data
  :feature:datecalculator --> :core:model
  :feature:glance --> :core:common
  :feature:glance --> :core:data
  :feature:glance --> :core:database
  :feature:glance --> :core:datastore
  :feature:glance --> :core:designsystem
  :feature:glance --> :core:evaluatto
  :feature:glance --> :core:model
  :feature:glance --> :core:navigation
  :feature:glance --> :core:themmo
  :feature:glance --> :core:ui
  :feature:programmer --> :core:common
  :feature:programmer --> :core:ui
  :feature:programmer --> :core:navigation
  :feature:programmer --> :core:designsystem
  :feature:programmer --> :core:datastore
  :feature:programmer --> :core:evaluatto

classDef focus fill:#769566,stroke:#fff,stroke-width:2px,color:#fff;
class :feature:settings focus
class :feature:glance focus
class :feature:calculator focus
class :feature:converter focus
class :feature:bodymass focus
class :feature:datecalculator focus
class :feature:timezone focus
class :feature:programmer focus
```
# Feature modules

Each tab in navigation drawer is a feature. This is specific for Unitto, you may modularize your app differently.