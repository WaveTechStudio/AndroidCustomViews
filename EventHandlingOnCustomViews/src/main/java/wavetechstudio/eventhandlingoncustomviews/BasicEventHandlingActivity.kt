package wavetechstudio.eventhandlingoncustomviews

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_basic_event_handling.*

class BasicEventHandlingActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                basicevent.visibility = View.VISIBLE
                advanceevent.visibility = View.GONE
                message.setText(R.string.basic_events)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                basicevent.visibility = View.GONE
                advanceevent.visibility = View.VISIBLE
                message.setText("")
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_event_handling)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
}
