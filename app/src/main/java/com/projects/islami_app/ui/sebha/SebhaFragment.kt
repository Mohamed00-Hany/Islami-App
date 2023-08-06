package com.projects.islami_app.ui.sebha

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.projects.islami_app.R

class SebhaFragment : Fragment() {
    var counter1:Int=0
    var counter2:Int=1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sebha, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val noTasbeh=view.findViewById<TextView>(R.id.noTasbeh)
        val tasbhButton=view.findViewById<TextView>(R.id.tasbhButton)
        tasbhButton.setOnClickListener{
            counter1++
            noTasbeh?.setText("$counter1")
            if((counter1==33&&(counter2==1||counter2==2))||(counter1==34&&(counter2==0)))
            {
                counter1=0
                counter2++
                if(counter2==1)
                {
                    tasbhButton.text="سبحان الله"
                }
                else if(counter2==2)
                {
                    tasbhButton.text="الحمد لله"
                }
                else if(counter2==3)
                {
                    tasbhButton.text="الله اكبر"
                    counter2=0
                }
            }
        }
    }
}