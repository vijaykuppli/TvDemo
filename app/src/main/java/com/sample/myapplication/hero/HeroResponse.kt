package com.sample.myapplication.hero

data class HeroResponse(
	val components: List<ComponentsItem?>? = null,
	val pageType: String? = null
)

data class ComponentsItem(
	val componentType: String? = null,
	val data: Data? = null
)

data class Action(
	val type: String? = null,
	val pageId: String? = null
)

data class ListItem(
	val image: String? = null,
	val action: Action? = null,
	val title: String? = null
)

data class Data(
	val name: String? = null,
	val list: List<ListItem?>? = null,
	val image: String? = null,
	val description: String? = null
)

