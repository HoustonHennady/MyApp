package com.example.myappktx.Repository

import com.example.ProductModel
import com.example.myappktx.Model.MainCategoryModel
import com.example.myappktx.Model.NewViewPagerModel
import com.example.myappktx.Model.SubCategoryModel
import com.example.myappktx.Model.UserModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MyFireStore {

    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val MAIN_CATEGORIES: String = "MainCategories"
    private val SUB_CATEGORIES: String = "SubCategories"
    private val PRODUCTS: String = "Products"
    private val VIEWPAGER: String = "ViewPager"

    suspend fun fetchMainCategory(): ArrayList<MainCategoryModel> = withContext(Dispatchers.IO) {
        val result = firestore
                .collection(MAIN_CATEGORIES)
                .get()
                .await()
        return@withContext result
                .toObjects(MainCategoryModel::class.java)
                as ArrayList<MainCategoryModel>
    }

    suspend fun fetchSubCategory(string: String): ArrayList<SubCategoryModel> = withContext(Dispatchers.IO) {
        val result = firestore
                .collection(SUB_CATEGORIES)
                .whereEqualTo("category",string)
                .get()
                .await()
        return@withContext result
                .toObjects(SubCategoryModel::class.java)
                as ArrayList<SubCategoryModel>
    }

    suspend fun getProductList(string: String): ArrayList<ProductModel> = withContext(Dispatchers.IO) {
        val result = firestore
                .collection(PRODUCTS).whereEqualTo("category",string)
                .get()
                .await()
        return@withContext result
                .toObjects(ProductModel::class.java)
                as ArrayList<ProductModel>
    }

    suspend fun getViewPagerPosts(): ArrayList<NewViewPagerModel> = withContext(Dispatchers.IO) {
        val result = firestore
                .collection(VIEWPAGER)
                .get()
                .await()
        return@withContext result
                .toObjects(NewViewPagerModel::class.java)
                as ArrayList<NewViewPagerModel>
    }

    suspend fun getViewPagerProductList(string: String): ArrayList<ProductModel> = withContext(Dispatchers.IO) {
        val result = firestore
                .collection("Products").whereEqualTo("name", string)
                .get()
                .await()
        return@withContext result
                .toObjects(ProductModel::class.java)
                as ArrayList<ProductModel>
    }
    suspend fun getSubCategoryList(): ArrayList<SubCategoryModel> = withContext(Dispatchers.IO) {
        val result = firestore.collection(SUB_CATEGORIES)
                .get()
                .await()
        return@withContext result
                .toObjects(SubCategoryModel::class.java)
                as ArrayList<SubCategoryModel>
    }
    suspend fun addNewProductToBase(product: ProductModel) = withContext(Dispatchers.IO){
        firestore.collection(PRODUCTS).document().set(product)
    }


    suspend fun setUserInfo(data:UserModel,userId: String) = withContext(Dispatchers.IO){
        firestore.collection("Users").document("wabC1SinwjYbGXQebJhTLoRcH912").set(data)
    }

    suspend fun getUserInfo(userId: String):UserModel = withContext(Dispatchers.IO){
        var result = firestore.collection("Users").document(userId).get().await()
        return@withContext result.toObject(UserModel::class.java) as UserModel
    }
}











