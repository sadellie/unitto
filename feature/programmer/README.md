# Programmer calculator module

Bit shifts and other crazy stuff.

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
  end
  subgraph :feature
    :feature:programmer["programmer"]
  end
  :sharedApp --> :feature:programmer
  :feature:programmer --> :core:common
  :feature:programmer --> :core:ui
  :feature:programmer --> :core:navigation
  :feature:programmer --> :core:designsystem
  :feature:programmer --> :core:datastore
  :feature:programmer --> :core:evaluatto

classDef focus fill:#769566,stroke:#fff,stroke-width:2px,color:#fff;
class :feature:programmer focus
```