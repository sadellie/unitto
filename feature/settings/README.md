# Settings module

App settings.

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
    :core:database["database"]
    :core:backup["backup"]
    :core:model["model"]
    :core:themmo["themmo"]
    :core:licenses["licenses"]
  end
  subgraph :feature
    :feature:settings["settings"]
  end
  :feature:settings --> :core:common
  :feature:settings --> :core:ui
  :feature:settings --> :core:navigation
  :feature:settings --> :core:designsystem
  :feature:settings --> :core:datastore
  :feature:settings --> :core:database
  :feature:settings --> :core:backup
  :feature:settings --> :core:model
  :feature:settings --> :core:themmo
  :feature:settings --> :core:licenses
  :app --> :feature:settings

classDef focus fill:#769566,stroke:#fff,stroke-width:2px,color:#fff;
class :feature:settings focus
```