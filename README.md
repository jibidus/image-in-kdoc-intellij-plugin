# image-in-kdoc

⚠️️️️Under development ⚠️️️️

![Build](https://github.com/jibidus/image-in-kdoc-intellij-plugin/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/MARKETPLACE_ID.svg)](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/MARKETPLACE_ID.svg)](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID)

<!-- Plugin description -->
With this plugin, Intellij can display images from kdoc based on markdown syntax (requested [here](https://youtrack.jetbrains.com/issue/KTIJ-13687/KDoc-support-inline-images) 6 years ago):

```kotlin
/**
 * Here is how to embed an image in kdoc:
 * 
 * ![Description](thunderCats.jpeg)
 */
```

… is rendered like this in rendered view:

![](kdoc%20sample.png)

* Local paths are relative to file where the kdoc is declared.
* External urls are supported.

<!-- Plugin description end -->

## Installation

- Using the IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "image-in-kdoc"</kbd> >
  <kbd>Install</kbd>
  
- Using JetBrains Marketplace:

  Go to [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID) and install it by clicking the <kbd>Install to ...</kbd> button in case your IDE is running.

  You can also download the [latest release](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID/versions) from JetBrains Marketplace and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

- Manually:

  Download the [latest release](https://github.com/jibidus/image-in-kdoc-intellij-plugin/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

## ToDo list

- [ ] Add icon to plugin
- [ ] Image not rendered in plugin description once installed
- [ ] Test with kdoc from another module
- [ ] Test with kdoc from external jar
- [ ] scrollbar not working when using rendered view in place
- [ ] Test with upper version of IntelliJ
- [ ] Bump IntelliJ
- [ ] Test plugin installation locally
- [ ] Write automated tests?
- [ ] Refactor implementation
- [ ] Remove warnings from buildPlugin gradle task
- [ ] Ensure all verify gradle tasks are OK
- [ ] [Publish a plugin manually](https://plugins.jetbrains.com/docs/intellij/publishing-plugin.html?from=IJPluginTemplate) for the first time.
- [ ] Set the `MARKETPLACE_ID` in the above README badges. You can obtain it once the plugin is published to JetBrains Marketplace.
- [ ] Set the [Plugin Signing](https://plugins.jetbrains.com/docs/intellij/plugin-signing.html?from=IJPluginTemplate) related [secrets](https://github.com/JetBrains/intellij-platform-plugin-template#environment-variables).
- [ ] Set the [Deployment Token](https://plugins.jetbrains.com/docs/marketplace/plugin-upload.html?from=IJPluginTemplate).
- [ ] Click the <kbd>Watch</kbd> button on the top of the [IntelliJ Platform Plugin Template][template] to be notified about releases containing new features and fixes.
