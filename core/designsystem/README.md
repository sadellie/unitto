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
    :core:designsystem["designsystem"]
    :core:ui["ui"]
    :core:navigation["navigation"]
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
  :feature:settings --> :core:designsystem
  :sharedApp --> :core:designsystem
  :androidApp --> :core:designsystem
  :feature:bodymass --> :core:designsystem
  :feature:calculator --> :core:designsystem
  :feature:converter --> :core:designsystem
  :feature:timezone --> :core:designsystem
  :feature:datecalculator --> :core:designsystem
  :core:ui --> :core:designsystem
  :feature:glance --> :core:designsystem
  :feature:programmer --> :core:designsystem
  :core:navigation --> :core:designsystem

classDef focus fill:#769566,stroke:#fff,stroke-width:2px,color:#fff;
class :core:designsystem focus
```