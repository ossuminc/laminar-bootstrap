package com.ossum.lamboot

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

import com.raquo.laminar.api.L.{*, given}


class LayoutTest extends AnyWordSpec with Matchers {

  "Layout" should {
    "lay out a simple container" in {
      val container = Layout.Container
      container mustBe div(cls := "container")
    }
  }
}
