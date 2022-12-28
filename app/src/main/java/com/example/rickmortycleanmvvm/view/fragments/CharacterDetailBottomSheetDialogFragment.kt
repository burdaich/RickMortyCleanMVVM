package com.example.rickmortycleanmvvm.view.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.amrdeveloper.lottiedialog.LottieDialog
import com.bumptech.glide.Glide
import com.example.domain.model.Character
import com.example.rickmortycleanmvvm.R
import com.example.rickmortycleanmvvm.databinding.CharacterDetailBottomSheetBinding
import com.example.rickmortycleanmvvm.viewmodel.CharacterDetailViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterDetailBottomSheetDialogFragment(
    private val character: Character,
    private val callback: (() -> Unit)?
) :
    BottomSheetDialogFragment() {

    private lateinit var viewModel: CharacterDetailViewModel
    private lateinit var binding: CharacterDetailBottomSheetBinding
    private lateinit var lottieDialog: LottieDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = CharacterDetailBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CharacterDetailViewModel::class.java]
        addDataToUI()
        buildListeners()
        observeResponse()

        if (!character.isFavorite) viewModel.getCharacterFavoriteByApiId(character.id)
    }

    private fun buildListeners() {
        binding.backArrowIB.setOnClickListener { dismiss() }
        binding.characterDetailAddToFavoritesB.setOnClickListener {
            viewModel.manageCharacter(character)
        }
    }

    private fun addDataToUI() {
        Glide.with(requireContext()).load(character.image).into(binding.characterDetailCIV)
        binding.characterDetailNameTV.text = character.name
        binding.characterDetailStatusTV.text = character.status
        binding.characterDetailSpeciesTV.text = character.species
        binding.characterDetailGenderTV.text = character.gender

        changeStarColorAndDescription(character.isFavorite)
    }

    private fun observeResponse() {
        lifecycleScope.launch {
            viewModel.state.collect {
                if (it.error.isEmpty()) {
                    changeStarColorAndDescription(it.character?.isFavorite)
                    if (it.isUpdated) showUpdateConfirmationDialog(it.character?.isFavorite)
                }
            }
        }
    }

    private fun showUpdateConfirmationDialog(favorite: Boolean?) {
        lottieDialog = LottieDialog(requireContext())
            .setAnimation(R.raw.staranimation)
            .setAnimationRepeatCount(LottieDialog.INFINITE)
            .setAutoPlayAnimation(true)
            .setTitleColor(Color.BLACK)
            .setCancelable(false)
            .addActionButton(buildLottieButton())
            .setDialogWidth(resources.getDimensionPixelSize(R.dimen.lottieWidth))
            .setDialogHeight(resources.getDimensionPixelSize(R.dimen.lottieHeight))

        if (favorite == true) {
            lottieDialog.setTitle(getString(R.string.character_added_favorite))
        } else {
            lottieDialog.setTitle(getString(R.string.character_removed_favorite))
            lottieDialog.reverseAnimationSpeed()
        }

        lottieDialog.show()
    }

    private fun buildLottieButton() =
        Button(requireContext()).apply {
            text = getString(R.string.accept)
            background =
                ContextCompat.getDrawable(requireContext(), R.drawable.button_corners)
            setOnClickListener {
                lottieDialog.dismiss()
                callback?.let {
                    it()
                    dismiss()
                }
            }
            setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        }

    private fun changeStarColorAndDescription(isFavorite: Boolean?) {
        TextViewCompat.setCompoundDrawableTintList(
            binding.characterDetailAddToFavoritesB,
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    if (isFavorite == true) R.color.yellow else R.color.grey
                )
            )
        )

        binding.characterDetailAddToFavoritesB.text =
            if (isFavorite == true) getString(R.string.remove_from_favorites) else getString(R.string.add_to_favorites)
    }

    companion object {
        fun show(
            fragmentManager: FragmentManager,
            character: Character,
            callback: (() -> Unit)? = null
        ) =
            CharacterDetailBottomSheetDialogFragment(character, callback).show(
                fragmentManager, this::
                class.java.name
            )
    }
}