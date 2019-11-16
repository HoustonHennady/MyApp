package com.example.myappktx.Repository

import com.example.ProductModel
import com.example.myappktx.Model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MyFireStore {

    private val fireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val MAIN_CATEGORIES: String = "MainCategories"
    private val SUB_CATEGORIES: String = "SubCategories"
    private val PRODUCTS: String = "Products"
    private val VIEWPAGER: String = "ViewPager"
    private val userLogin = "user1@user1.com"
    private val userPassword = "12345678"


    suspend fun signin(): String? = withContext(Dispatchers.IO) {
        firebaseAuth.signInWithEmailAndPassword(userLogin, userPassword).await()
        return@withContext firebaseAuth.currentUser?.uid
    }

    suspend fun fetchMainCategory(): ArrayList<MainCategoryModel> = withContext(Dispatchers.IO) {
        val result =
            fireStore
                .collection(MAIN_CATEGORIES)
                .get()
                .await()
        return@withContext result
            .toObjects(MainCategoryModel::class.java)
                as ArrayList<MainCategoryModel>
    }

    suspend fun fetchSubCategory(string: String): ArrayList<SubCategoryModel> =
        withContext(Dispatchers.IO) {
            val result = fireStore
                .collection(SUB_CATEGORIES)
                .whereEqualTo("category", string)
                .get()
                .await()
            return@withContext result
                .toObjects(SubCategoryModel::class.java)
                    as ArrayList<SubCategoryModel>
        }

    suspend fun getProductList(string: String): ArrayList<ProductModel> =
        withContext(Dispatchers.IO) {
            val result = fireStore
                .collection(PRODUCTS).whereEqualTo("category", string)
                .get()
                .await()
            return@withContext result
                .toObjects(ProductModel::class.java)
                    as ArrayList<ProductModel>
        }

    suspend fun getViewPagerPosts(): ArrayList<NewViewPagerModel> = withContext(Dispatchers.IO) {
        val result = fireStore
            .collection(VIEWPAGER)
            .get()
            .await()
        return@withContext result
            .toObjects(NewViewPagerModel::class.java)
                as ArrayList<NewViewPagerModel>
    }

    suspend fun getViewPagerProductList(string: String): ArrayList<ProductModel> =
        withContext(Dispatchers.IO) {
            val result = fireStore
                .collection("Products").whereEqualTo("name", string)
                .get()
                .await()
            return@withContext result
                .toObjects(ProductModel::class.java)
                    as ArrayList<ProductModel>
        }

    suspend fun getSubCategoryList(): ArrayList<SubCategoryModel> = withContext(Dispatchers.IO) {
        val result =
            fireStore
                .collection(SUB_CATEGORIES)
                .get()
                .await()
        return@withContext result
            .toObjects(SubCategoryModel::class.java)
                as ArrayList<SubCategoryModel>
    }

    suspend fun addNewProductToBase(product: ProductModel) = withContext(Dispatchers.IO) {
        fireStore
            .collection(PRODUCTS)
            .document()
            .set(product)
    }

    suspend fun setUserInfo(data: UserModel, userId: String) = withContext(Dispatchers.IO) {
        fireStore
            .collection("Users")
            .document("wabC1SinwjYbGXQebJhTLoRcH912")
            .set(data)
    }

    suspend fun getUserInfo(userId: String): UserModel = withContext(Dispatchers.IO) {
        var result =
            fireStore
                .collection("Users")
                .document(userId)
                .get()
                .await()
        return@withContext result
            .toObject(UserModel::class.java)
                as UserModel
    }

    suspend fun addNewOrders(order: OrderModel) = withContext(Dispatchers.IO) {
        fireStore
            .collection("Orders")
            .add(order)
            .await()
    }

    suspend fun getOrderList(userId: String): ArrayList<OrderModel> = withContext(Dispatchers.IO) {
        var result =
            fireStore
                .collection("Orders")
                .whereEqualTo("userId", userId)
                .get()
                .await()
        return@withContext result
            .toObjects(OrderModel::class.java)
                as ArrayList<OrderModel>
    }
}
