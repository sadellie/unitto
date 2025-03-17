# Evaluatto™

Best expression evaluator. Do not contribute! Coupled with token system.

## Graph

```mermaid
%%{
  init: {
    'theme': 'dark'
  }
}%%

graph LR
  subgraph :core
    :core:evaluatto["evaluatto"]
    :core:data["data"]
    :core:common["common"]
  end
  subgraph :feature
    :feature:converter["converter"]
    :feature:glance["glance"]
    :feature:graphing["graphing"]
    :feature:calculator["calculator"]
  end
  :feature:converter --> :core:evaluatto
  :core:data --> :core:evaluatto
  :feature:glance --> :core:evaluatto
  :feature:graphing --> :core:evaluatto
  :core:evaluatto --> :core:common
  :feature:calculator --> :core:evaluatto

classDef focus fill:#769566,stroke:#fff,stroke-width:2px,color:#fff;
class :core:evaluatto focus
```