package com.ossum.lamboot

import com.raquo.domtypes.generic.Modifier
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom
import com.raquo.domtypes.generic.Modifier
import com.raquo.laminar.api.L.*

/** Generalized Things for Bootstrap overall */
object Bootstrap {

  // This is what the #app.target selector gets replaced with and is the
  // entry point to your entire web app. The modifiers passed in form
  // the page produced. Typically a single, top level template element
  // is passed in.
  def main(modifiers: Modifier[ReactiveHtmlElement[dom.html.Div]]*): Unit =
    val containerNode = dom.document.querySelector("#app.target")
    val rootElement = div(modifiers)
    render(containerNode, rootElement)

  enum Option(value: Boolean):
    case
      Rounded, // Enables predefined border -radius styles on various components.
      Shadows, // Enables predefined decorative box - shadow styles on various components.Does not affect box -shadows
               // used for focus states
      Gradients, // Enables predefined gradients via background -image styles on various components.
      Transitions, // Enables predefined transitions on various components.
      ReducedMotion, // Enables the prefers -reduced - motion media query which suppresses certain animations or
                     // transitions based on the users
    ’browser / operating system preferences.
      $enable - grid - classes
    true(default) or false Enables the generation of CSS classes
    for the grid system(e.g.
    .row
    ,.col - md - 1
    , etc
    .).
    $enable - container - classes
    true(default) or false Enables the generation of CSS classes
    for layout containers
    .(New in v5
    .2
    .0
    )
    $enable - caret
    true(default) or false Enables pseudo element caret on
    .dropdown - toggle.
      $enable - button - pointers
    true(default) or false Add
    “hand
    ”cursor to non - disabled button elements.
      $enable - rfs
    true(default) or false Globally enables RFS
    .
    $enable - validation - icons
    true(default) or false Enables background - image icons within textual inputs and some custom forms
    for validation states
    .
    $enable - negative - margins
    true or false(default) Enables the generation of negative margin utilities
    .
    $enable - deprecation - messages
    true(default) or false Set to
    false to hide warnings when using any of the deprecated mixins and functions that are planned to be removed in v6.
      $enable - important - utilities
    true(default) or false Enables the ! important suffix in utility classes.
      $enable - smooth - scroll
    true(default) or false Applies scroll - behavior: smooth
    globally
    , except
    for users asking for reduced motion through prefers - reduced - motion media query
}
