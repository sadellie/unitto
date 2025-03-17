# Calculator module

Calculator tool. Main feature. 

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
    :core:evaluatto["evaluatto"]
  end
  subgraph :feature
    :feature:calculator["calculator"]
    :feature:datecalculator["datecalculator"]
  end
  :app --> :feature:calculator
  :app --> :feature:datecalculator
  :feature:datecalculator --> :core:common
  :feature:datecalculator --> :core:ui
  :feature:datecalculator --> :core:navigation
  :feature:datecalculator --> :core:designsystem
  :feature:datecalculator --> :core:datastore
  :feature:datecalculator --> :core:data
  :feature:datecalculator --> :core:model
  :feature:calculator --> :core:common
  :feature:calculator --> :core:ui
  :feature:calculator --> :core:navigation
  :feature:calculator --> :core:designsystem
  :feature:calculator --> :core:datastore
  :feature:calculator --> :core:evaluatto
  :feature:calculator --> :core:data
  :feature:calculator --> :core:model

classDef focus fill:#769566,stroke:#fff,stroke-width:2px,color:#fff;
class :feature:calculator focus
class :feature:datecalculator focus
```