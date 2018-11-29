package by.marpod.cdekapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import by.marpod.cdekapp.R
import by.marpod.cdekapp.base.BaseFragment
import by.marpod.cdekapp.databinding.FragmentInputCitiesBinding
import by.marpod.cdekapp.util.autoCleared

class InputCitiesFragment : BaseFragment() {

    override val layout: Int
        get() = R.layout.fragment_input_cities

    var binding by autoCleared<FragmentInputCitiesBinding>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentInputCitiesBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@InputCitiesFragment)

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnSubmit.setOnClickListener {
            findNavController().navigate(R.id.action_inputCitiesFragment_to_availableRoutesFragment)
        }
    }
}