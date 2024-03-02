package com.example.mynavigationdrawer

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mynavigationdrawer.databinding.ActivityMainBinding
import de.hdodenhof.circleimageview.CircleImageView

/*
Pada activity_main.xml tag Include harus berada di atas baru NavigationView, jika tidak akan error
 */
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var profileCircleImageView: CircleImageView
    private var profileImageUrl = "https://lh3.googleusercontent.com/-4qy2DfcXBoE/AAAAAAAAAAI/AAAAAAAABi4/rY-jrtntAi4/s640-il/photo.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        // ini adalah snackbar, tidak jauh beda dengan toast tapi disini bisa menambahkan action lain
        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                // ini untuk snackbar tanpa ada action khusus
                //.setAction("Action", null).show()
                // ini untuk snackbar dengan action khusus
                .setAction("Action") { Toast.makeText(this@MainActivity, "Halo ini action dari snackbar", Toast.LENGTH_SHORT).show()}
                .show()
        }


        val drawerLayout: DrawerLayout = binding.drawerLayout
        // NavigationView menampung semua menu dan sebuah header
        val navView: NavigationView = binding.navView
        // Kenapa harus 0? Ini karena indeks header berada pada susunan teratas dari kumpulan list menu yang terdapat pada NavigationView
        profileCircleImageView = navView.getHeaderView(0).findViewById(R.id.imageView)
        Glide.with(this)
            .load(profileImageUrl)        // mendapatkan gambar dari url dan di set di circle
            .into(profileCircleImageView)


        val navController = findNavController(R.id.nav_host_fragment_content_main)


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        // ini merupakan proses otomatis perpindahan fragment tanpa perlu memanggilnya satu persatu dengan menyamakan namanya pada menu juga navigation
        appBarConfiguration = AppBarConfiguration( // dia menangkap id-id pada navigation drawer (menu dan navigation), jika isinya kosong maka akan menampilkan panah kembali
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_subway, R.id.nav_cart // untuk perpindahan fragment dari id" tsb
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration) // untuk mengatur judul-judul navigasi
        navView.setupWithNavController(navController) // agar NavigationDrawer menampilkan fragment yang sesuai dengan yang di pilih
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    // code ini menunjukan start destination pada mobile_navigation, untuk mencegah aplikasi keluar. jadi dia akan kembali ke tampilan asal sesuai dengan set dibawah
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}

/*
android:checkableBehavior= "single" akan berguna untuk memberikan highlight pada menu yang aktif di menu" aktivity_main_drawer
pada nested item (item dalam item) menampilan tittle dari sebelum menu atau item outer yang memudahkan pengelompokan, untuk memberikan seperti diatas tambahkan checkable=”true”
 */