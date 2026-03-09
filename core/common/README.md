# Common module

Utilities, objects and resources shared between modules. 

## Graph

```mermaid
%%{
  init: {
    'theme': 'dark'
  }
}%%

graph LR
  subgraph :core
    :core:database["database"]
    :core:common["common"]
    :core:evaluatto["evaluatto"]
    :core:datastore["datastore"]
    :core:model["model"]
    :core:data["data"]
    :core:ui["ui"]
    :core:navigation["navigation"]
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
  :core:database --> :core:common
  :core:common --> :kt-math
  :feature:settings --> :core:common
  :core:evaluatto --> :core:common
  :core:datastore --> :core:common
  :feature:bodymass --> :core:common
  :core:model --> :core:common
  :feature:calculator --> :core:common
  :feature:converter --> :core:common
  :feature:timezone --> :core:common
  :feature:datecalculator --> :core:common
  :core:data --> :core:common
  :core:ui --> :core:common
  :feature:glance --> :core:common
  :feature:programmer --> :core:common
  :core:navigation --> :core:common

classDef focus fill:#769566,stroke:#fff,stroke-width:2px,color:#fff;
class :core:common focus
```