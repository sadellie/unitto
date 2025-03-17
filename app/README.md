# App module

Main module that glues everything together. Navigation host and Themmo live here.

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
    :core:designsystem["designsystem"]
    :core:ui["ui"]
    :core:themmo["themmo"]
    :core:datastore["datastore"]
  end
  subgraph :feature
    :feature:bodymass["bodymass"]
    :feature:calculator["calculator"]
    :feature:converter["converter"]
    :feature:datecalculator["datecalculator"]
    :feature:glance["glance"]
    :feature:graphing["graphing"]
    :feature:timezone["timezone"]
    :feature:settings["settings"]
  end
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

classDef focus fill:#769566,stroke:#fff,stroke-width:2px,color:#fff;
class :app focus
```