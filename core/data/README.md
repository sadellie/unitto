# Data module

Main data provider for feature modules. All repositories are stored in one module for simplicity.

## Graph

```mermaid
%%{
  init: {
    'theme': 'dark'
  }
}%%

graph LR
  subgraph :core
    :core:data["data"]
    :core:datastore["datastore"]
    :core:model["model"]
    :core:common["common"]
    :core:evaluatto["evaluatto"]
    :core:database["database"]
    :core:remote["remote"]
  end
  subgraph :feature
    :feature:settings["settings"]
    :feature:calculator["calculator"]
    :feature:converter["converter"]
    :feature:timezone["timezone"]
    :feature:datecalculator["datecalculator"]
    :feature:glance["glance"]
  end
  :feature:settings --> :core:data
  :sharedApp --> :core:data
  :core:datastore --> :core:data
  :feature:calculator --> :core:data
  :feature:converter --> :core:data
  :feature:timezone --> :core:data
  :feature:datecalculator --> :core:data
  :core:data --> :core:model
  :core:data --> :core:common
  :core:data --> :core:evaluatto
  :core:data --> :core:database
  :core:data --> :core:remote
  :feature:glance --> :core:data

classDef focus fill:#769566,stroke:#fff,stroke-width:2px,color:#fff;
class :core:data focus
```