# Time zone module

Tool to convert time zones. Android only

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
    :core:data["data"]
    :core:model["model"]
  end
  subgraph :feature
    :feature:timezone["timezone"]
  end
  :app --> :feature:timezone
  :feature:timezone --> :core:common
  :feature:timezone --> :core:ui
  :feature:timezone --> :core:navigation
  :feature:timezone --> :core:designsystem
  :feature:timezone --> :core:datastore
  :feature:timezone --> :core:data
  :feature:timezone --> :core:model

classDef focus fill:#769566,stroke:#fff,stroke-width:2px,color:#fff;
class :feature:timezone focus
```