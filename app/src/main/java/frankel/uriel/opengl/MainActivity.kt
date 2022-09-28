package frankel.uriel.opengl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import frankel.uriel.opengl.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.surfaceView.setEGLConfigChooser(8,8,8,8,16,0)
        binding.surfaceView.setEGLContextClientVersion(2)
        binding.surfaceView.setRenderer(MyFirstRenderer(this))

    }

    override fun onPause() {
        super.onPause()
        binding.surfaceView.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.surfaceView.onResume()
    }

}