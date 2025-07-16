package uz.mrsolijon.weatherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.yariksoffice.lingver.Lingver
import uz.mrsolijon.weatherapp.R
import uz.mrsolijon.weatherapp.databinding.FragmentSettingsBinding
import uz.mrsolijon.weatherapp.language.LanguageEnum

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingsBinding.bind(view)

        binding.settingsBackButton.setOnClickListener {
            findNavController().popBackStack()
        }

        selectLanguage()
    }

    private fun selectLanguage() {
        binding.apply {
            uzbBtn.setOnClickListener {
                selectedLanguage(LanguageEnum.UZ)
            }
            ruBtn.setOnClickListener {
                selectedLanguage(LanguageEnum.RU)
            }
            ruzBtn.setOnClickListener {
                selectedLanguage(LanguageEnum.UZ_rUZ,"uz")
            }

        }
        updateLanguageButton(getLocalLang())
    }

    private fun selectedLanguage(language: LanguageEnum, vaiant: String = "") {
        updateLanguageButton(language)
        Lingver.getInstance().setLocale(requireContext(), language.langCode, vaiant)
        binding.titleTv.text = getText(R.string.select_lang)
//        requireActivity().recreate()
    }

    private fun updateLanguageButton(enabledItem: LanguageEnum) {
        binding.apply {
            ruCheckIcon.isVisible = enabledItem == LanguageEnum.RU
            uzCheckIcon.isVisible = enabledItem == LanguageEnum.UZ
            ruzCheckIcon.isVisible = enabledItem == LanguageEnum.UZ_rUZ
            ruBtn.setBackgroundResource(getBackgroundResource(enabledItem == LanguageEnum.RU))
            uzbBtn.setBackgroundResource(getBackgroundResource(enabledItem == LanguageEnum.UZ))
            ruzBtn.setBackgroundResource(getBackgroundResource(enabledItem == LanguageEnum.UZ_rUZ))
        }
    }

    private fun getLocalLang(): LanguageEnum {
        when (Lingver.getInstance().getLocale().toString()) {
            "uz" -> {
                return LanguageEnum.UZ
            }

            "ru" -> {
                return LanguageEnum.RU
            }
            "uz_CR" -> {
                return LanguageEnum.UZ_rUZ
            }
        }
        return LanguageEnum.UZ
    }

    @DrawableRes
    private fun getBackgroundResource(isSelected: Boolean): Int {
        return if (isSelected) R.drawable.select_lang_card_background_check
        else R.drawable.select_lang_card_background
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}