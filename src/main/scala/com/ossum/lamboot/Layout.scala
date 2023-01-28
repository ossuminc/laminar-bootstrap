package com.ossum.lamboot

import com.raquo.laminar.api.L.{*, given}

object Layout {
  object Container  {
    def apply: Div = div(cls := "container")
    def fluid: Div = div(cls := "container-fluid")
    def sm: Div = div(cls := "container-sm")
    def md: Div = div(cls := "container-md")
    def lg: Div = div(cls := "container-ld")
    def xl: Div = div(cls := "container-xl")
    def xxl: Div = div(cls := "container-xxl")
  }

  object Grid {
    enum Tier:
      case none , xs, sm, md, lg, xl, xxl

    final val auto: Width = "auto"

    type Width = 0 | 1| 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | "auto"

    val NoWidth: Width = 0

    def row(mods: Mod[Div]*): Div = div(cls := "row", mods)

    def rowCol(width: Width = 0) = div(cls := s"row-cols-$width")
    def col(tier: Tier = Tier.none, width: Width = NoWidth) : Div =
      div(cls := colClass(tier, width))

    private def colClass(tier: Tier = Tier.none, width: Width = NoWidth): String =
      if tier == Tier.none  then
        if width == NoWidth then
          "col"
        else
          s"col-${width.toString}"
      else if width == NoWidth then
        s"col-${tier.toString}"
      else
        s"col-${tier.toString}-${width.toString}"

    def col(pair1: (Tier, Width), pair2: (Tier,Width)): Div =
      val (tier1: Tier, width1: Width) = pair1 : @unchecked
      val (tier2: Tier, width2: Width) = pair2 : @unchecked
      div(cls := colClass(tier1,width1)+" "+colClass(tier2, width2))

    def rowColsAuto: Div = div(cls := "row row-cols-auto")
    def rowCols(width: Width = 0): Div =
      if width == NoWidth then
        div(cls := "row")
      else
        div(cls := s"row row-cols-${width.toString}")
  }
}
