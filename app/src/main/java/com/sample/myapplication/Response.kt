package com.sample.myapplication

data class Response(
	val glossary: Glossary? = null
)

data class GlossDiv(
	val glossList: GlossList? = null,
	val title: String? = null
)

data class GlossDef(
	val para: String? = null,
	val glossSeeAlso: List<String?>? = null
)

data class GlossList(
	val glossEntry: GlossEntry? = null
)

data class GlossEntry(
	val glossTerm: String? = null,
	val glossSee: String? = null,
	val sortAs: String? = null,
	val glossDef: GlossDef? = null,
	val iD: String? = null,
	val acronym: String? = null,
	val abbrev: String? = null
)

data class Glossary(
	val title: String? = null,
	val glossDiv: GlossDiv? = null
)

