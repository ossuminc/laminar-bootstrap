package com.ossum.lamboot

import com.raquo.domtypes.generic.Modifier
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom
import com.raquo.domtypes.generic.Modifier
import com.raquo.laminar.api.L.*

/** Generalized Things for Bootstrap overall */
object Bootstrap:

  // This is what the #app.target selector gets replaced with and is the
  // entry point to your entire web app. The modifiers passed in form
  // the page produced. Typically a single, top level template element
  // is passed in.
  def main(modifiers: Modifier[ReactiveHtmlElement[dom.html.Div]]*): Unit =
    val containerNode = dom.document.querySelector("#app.target")
    val rootElement = div(modifiers)
    render(containerNode, rootElement)
/*
  enum Option:
    case Rounded, // Enables predefined border -radius styles on various components.
      Shadows, // Enables predefined decorative box - shadow styles on various components.Does not affect box -shadows
      // used for focus states
      Gradients, // Enables predefined gradients via background -image styles on various components.
      Transitions, // Enables predefined transitions on various components.
      ReducedMotion, // Enables the prefers -reduced - motion media query which suppresses certain animations or
      // transitions based on the users browser / operating system preferences.
      GridClasses, // Enables the generation of CSS classes for the grid system(e.g..row,.col - md - 1, etc.).
      ContainerClasses, // Enables the generation of CSS classes for layout containers.(New in v5.2.0 )
      Caret, // Enables pseudo element caret on .dropdown - toggle.
      ButtonPointers, // Add “hand” cursor to non-disabled button elements.
      RFS, // Globally enables Responsive Font Size
      ValidationIcons, // Enables background - image icons within textual inputs and some custom forms for validation
      // states.
      NegativeMargins, // Enables the generation of negative margin utilities.
      DeprecationMessages, // Set to false to hide warnings when using any of the deprecated mixins and functions that
      // are planned to be removed in v6.
      ImportantUtilities, // Enables the ! important suffix in utility classes.
      SmoothScroll // Applies scroll - behavior: smooth globally, except for users asking for reduced motion through
      // prefers - reduced - motion media query
*/
