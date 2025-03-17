# Unit converter module

Unit and currency converter. Main feature.

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
  end
  subgraph :feature
    :feature:converter["converter"]
  end
  :feature:converter --> :core:common
  :feature:converter --> :core:ui
  :feature:converter --> :core:navigation
  :feature:converter --> :core:designsystem
  :feature:converter --> :core:datastore
  :feature:converter --> :core:evaluatto
  :feature:converter --> :core:data
  :feature:converter --> :core:model
  :app --> :feature:converter

classDef focus fill:#769566,stroke:#fff,stroke-width:2px,color:#fff;
class :feature:converter focus
```