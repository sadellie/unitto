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
    :feature:settings["settings"]
    :feature:bodymass["bodymass"]
    :feature:calculator["calculator"]
    :feature:converter["converter"]
    :feature:timezone["timezone"]
    :feature:datecalculator["datecalculator"]
    :feature:glance["glance"]
    :feature:programmer["programmer"]
  end
  :feature:settings --> :core:datastore
  :sharedApp --> :core:datastore
  :androidApp --> :core:datastore
  :core:datastore --> :core:common
  :core:datastore --> :core:model
  :core:datastore --> :core:themmo
  :core:datastore --> :core:data
  :core:datastore --> :core:navigation
  :feature:bodymass --> :core:datastore
  :feature:calculator --> :core:datastore
  :feature:converter --> :core:datastore
  :feature:timezone --> :core:datastore
  :feature:datecalculator --> :core:datastore
  :feature:glance --> :core:datastore
  :feature:programmer --> :core:datastore
  :core:backup --> :core:datastore

classDef focus fill:#769566,stroke:#fff,stroke-width:2px,color:#fff;
class :core:datastore focus
```