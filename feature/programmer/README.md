# Programmer calculator module

Bit shifts and other crazy stuff.

## Graph

[//]: # (TODO update graph)
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
  end
  subgraph :feature
    :feature:bodymass["bodymass"]
  end
  :sharedApp --> :feature:bodymass
  :feature:bodymass --> :core:common
  :feature:bodymass --> :core:ui
  :feature:bodymass --> :core:navigation
  :feature:bodymass --> :core:designsystem
  :feature:bodymass --> :core:datastore

classDef focus fill:#769566,stroke:#fff,stroke-width:2px,color:#fff;
class :feature:bodymass focus
```