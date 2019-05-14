package com.example.belajaronlineapplication

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_lainnya.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class LainnyaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_lainnya, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rlLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val signInIntent = Intent(requireActivity(), SignInActivity::class.java)
            requireActivity().startActivity(signInIntent)
        }

        rlHubungi.setOnClickListener {
            val hubungiIntent = Intent(requireActivity(), ComingSoonActivity::class.java)
            requireActivity().startActivity(hubungiIntent)
        }
        rlTentang.setOnClickListener {
            val tentangIntent = Intent(requireActivity(), ComingSoonActivity::class.java)
            requireActivity().startActivity(tentangIntent)
        }
    }
}