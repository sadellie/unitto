# Architecture

This page contains information about app architecture.

## Module ~~spaghetti~~ Graph

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
    :core:evaluatto["evaluatto"]
    :core:data["data"]
    :core:model["model"]
    :core:database["database"]
    :core:backup["backup"]
    :core:themmo["themmo"]
    :core:licenses["licenses"]
    :core:remote["remote"]
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
  :core:navigation --> :core:common
  :core:navigation --> :core:designsystem
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
  :app --> :core:navigation
  :app --> :core:designsystem
  :app --> :core:ui
  :app --> :core:themmo
  :app --> :core:datastore
  :app --> :feature:bodymass
  :app --> :feature:calculator
  :app --> :feature:converter
  :app --> :feature:datecalculator
  :app --> :feature:glance
  :app --> :feature:graphing
  :app --> :feature:timezone
  :app --> :feature:settings
  :core:model --> :core:common
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
  :core:data --> :core:model
  :core:data --> :core:common
  :core:data --> :core:remote
  :core:data --> :core:evaluatto
  :core:data --> :core:database
  :core:data --> :core:themmo
  :core:ui --> :core:common
  :core:ui --> :core:navigation
  :core:ui --> :core:designsystem
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
  :core:evaluatto --> :core:common
  :core:datastore --> :core:common
  :core:datastore --> :core:model
  :core:datastore --> :core:themmo
  :core:datastore --> :core:data
  :core:datastore --> :core:navigation
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
  :core:backup --> :core:database
  :core:backup --> :core:datastore
```