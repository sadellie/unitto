# Themmo

Advanced theming controls. ALWAYS test on different Android versions!

## Graph

```mermaid
%%{
  init: {
    'theme': 'dark'
  }
}%%

graph LR
  subgraph :core
    :core:themmo["themmo"]
    :core:data["data"]
    :core:datastore["datastore"]
  end
  subgraph :feature
    :feature:settings["settings"]
  end
  :feature:settings --> :core:themmo
  :app --> :core:themmo
  :core:data --> :core:themmo
  :core:datastore --> :core:themmo

classDef focus fill:#769566,stroke:#fff,stroke-width:2px,color:#fff;
class :core:themmo focus
```