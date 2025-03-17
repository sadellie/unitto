# Design system module

Basic branding resources: color scheme, icons and shapes. All icons are converted using [Valkyrie](https://github.com/ComposeGears/Valkyrie/). Some icons are hand-drawn by me, their source file is not open source.

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
    :core:designsystem["designsystem"]
    :core:ui["ui"]
  end
  subgraph :feature
    :feature:converter["converter"]
    :feature:settings["settings"]
    :feature:datecalculator["datecalculator"]
    :feature:timezone["timezone"]
    :feature:glance["glance"]
    :feature:graphing["graphing"]
    :feature:bodymass["bodymass"]
    :feature:calculator["calculator"]
  end
  :core:navigation --> :core:designsystem
  :feature:converter --> :core:designsystem
  :feature:settings --> :core:designsystem
  :app --> :core:designsystem
  :feature:datecalculator --> :core:designsystem
  :feature:timezone --> :core:designsystem
  :core:ui --> :core:designsystem
  :feature:glance --> :core:designsystem
  :feature:graphing --> :core:designsystem
  :feature:bodymass --> :core:designsystem
  :feature:calculator --> :core:designsystem

classDef focus fill:#769566,stroke:#fff,stroke-width:2px,color:#fff;
class :core:designsystem focus
```