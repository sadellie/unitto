# Datastore module

Module with user settings. ALWAYS CHECK if they are stored and restored properly if you make any changes here.

## Graph

```mermaid
%%{
  init: {
    'theme': 'dark'
  }
}%%

graph LR
  subgraph :core
    :core:datastore["datastore"]
    :core:common["common"]
    :core:model["model"]
    :core:themmo["themmo"]
    :core:data["data"]
    :core:navigation["navigation"]
    :core:backup["backup"]
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
  :feature:converter --> :core:datastore
  :feature:settings --> :core:datastore
  :app --> :core:datastore
  :feature:datecalculator --> :core:datastore
  :feature:timezone --> :core:datastore
  :feature:glance --> :core:datastore
  :feature:graphing --> :core:datastore
  :core:datastore --> :core:common
  :core:datastore --> :core:model
  :core:datastore --> :core:themmo
  :core:datastore --> :core:data
  :core:datastore --> :core:navigation
  :feature:bodymass --> :core:datastore
  :feature:calculator --> :core:datastore
  :core:backup --> :core:datastore

classDef focus fill:#769566,stroke:#fff,stroke-width:2px,color:#fff;
class :core:datastore focus
```