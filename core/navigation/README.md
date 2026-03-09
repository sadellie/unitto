# Navigation module

App screen routes and shortcuts. Unitto uses type-safe navigation. This module doesn't have host.

## Graph

```mermaid
%%{
  init: {
    'theme': 'dark'
  }
}%%

graph LR
  subgraph :core
    :core:navigation["navigation"]
    :core:datastore["datastore"]
    :core:ui["ui"]
    :core:common["common"]
    :core:designsystem["designsystem"]
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
  :feature:settings --> :core:navigation
  :sharedApp --> :core:navigation
  :androidApp --> :core:navigation
  :core:datastore --> :core:navigation
  :feature:bodymass --> :core:navigation
  :feature:calculator --> :core:navigation
  :feature:converter --> :core:navigation
  :feature:timezone --> :core:navigation
  :feature:datecalculator --> :core:navigation
  :core:ui --> :core:navigation
  :feature:glance --> :core:navigation
  :feature:programmer --> :core:navigation
  :core:navigation --> :core:common
  :core:navigation --> :core:designsystem

classDef focus fill:#769566,stroke:#fff,stroke-width:2px,color:#fff;
class :core:navigation focus
```