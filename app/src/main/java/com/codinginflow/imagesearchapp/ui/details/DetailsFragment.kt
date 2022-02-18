package com.codinginflow.imagesearchapp.ui.details

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.codinginflow.imagesearchapp.R
import com.codinginflow.imagesearchapp.databinding.FragmentGalleryBinding
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args by navArgs<DetailsFragmentArgs>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentGalleryBinding.bind(view)

        binding.apply {
            val photo = args.photo

            Glide.with(this@DetailsFragment)
                .listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        text_view_creator.isVisible = true
                        text_view_description.isVisible = photo.description != null
                        return false
                    }

                })
                .load(photo.urls.full)
                .error(R.drawable.ic_error)

                .into(image_view)



            val uri  = Uri.parse(photo.user.attributionUrl)

            val intent = Intent(Intent.ACTION_VIEW,uri)

           text_view_creator.apply {
                text = "This is created by ${photo.user.name} on Unsplash"

                setOnClickListener {
                    context.startActivity(intent)
                }
                paint.isUnderlineText = true


            }


        }
    }
}