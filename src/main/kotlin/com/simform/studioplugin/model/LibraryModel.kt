package com.simform.studioplugin.model

data class LibraryModel(val name: String, val link: String) {
    override fun toString(): String {
        return name
    }

    companion object {
        fun getAndroidLibraries(): ArrayList<LibraryModel> {
            val listModel = ArrayList<LibraryModel>()

            listModel.add(
                LibraryModel(
                    "SSPullToRefresh",
                    "https://github.com/SimformSolutionsPvtLtd/SSPullToRefresh"
                )
            )
            listModel.add(LibraryModel("SSSpinnerButton", "https://github.com/SimformSolutionsPvtLtd/SSSpinnerButton"))
            listModel.add(
                LibraryModel(
                    "SSCustomCameraControl",
                    "https://github.com/SimformSolutionsPvtLtd/SSCustomCameraControl"
                )
            )
            listModel.add(LibraryModel("SSImagePicker", "https://github.com/SimformSolutionsPvtLtd/SSImagePicker"))
            listModel.add(
                LibraryModel(
                    "SSExpandableRecyclerView",
                    "https://github.com/SimformSolutionsPvtLtd/SSExpandableRecylerView"
                )
            )
            listModel.add(
                LibraryModel(
                    "SSAndroidNeumorphicKit",
                    "https://github.com/SimformSolutionsPvtLtd/SSAndroidNeumorphicKit"
                )
            )
            listModel.add(LibraryModel("EasyAdapter", "https://github.com/mkrupal09/EasyAdapter"))
            return listModel
        }

        fun getFlutterLibraries(): ArrayList<LibraryModel> {
            val listModel = ArrayList<LibraryModel>()

            listModel.add(
                LibraryModel(
                    "Flutter Showcase",
                    "https://github.com/SimformSolutionsPvtLtd/flutter_showcaseview"
                )
            )
            return listModel
        }
    }


}


