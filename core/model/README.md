# Model module

Common classes and interfaces that need to be shared between modules (didn't fit in `:core:data` module)

## Graph

```mermaid
%%{
  init: {
    'theme': 'dark'
  }
}%%

graph LR
  subgraph :core
    :core:model["model"]
    :core:common["common"]
    :core:data["data"]
    :core:datastore["datastore"]
  end
  subgraph :feature
    :feature:converter["converter"]
    :feature:settings["settings"]
    :feature:datecalculator["datecalculator"]
    :feature:timezone["timezone"]
    :feature:graphing["graphing"]
    :feature:calculator["calculator"]
  end
  :feature:converter --> :core:model
  :feature:settings --> :core:model
  :core:model --> :core:common
  :feature:datecalculator --> :core:model
  :feature:timezone --> :core:model
  :core:data --> :core:model
  :feature:graphing --> :core:model
  :core:datastore --> :core:model
  :feature:calculator --> :core:model

classDef focus fill:#769566,stroke:#fff,stroke-width:2px,color:#fff;
class :core:model focus
```