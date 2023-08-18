package com.diplomproject.view.main_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.diplomproject.R
import com.diplomproject.model.data_word_request.DataModel
import com.diplomproject.utils.ui.viewById

class MainAdapter(

    private var onListItemClickListener: (DataModel) -> Unit,
    private var putInFavoriteListListener: (DataModel, Int, Boolean) -> Unit,
    private var playArticulationClickListener: (String) -> Unit,
) : RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>() {

    private var data: List<DataModel> = listOf()
    fun setData(data: List<DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data.get(position))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val header_textview_recycler_item by viewById<TextView>(R.id.header_textview_recycler_item)
        private val description_textview_recycler_item by viewById<TextView>(R.id.description_textview_recycler_item)
        private val transcription_textview_recycler_item by viewById<TextView>(R.id.transcription_textview_recycler_item)
        private val set_favorite by viewById<AppCompatImageButton>(R.id.set_favorite)
        private val play_articulation by viewById<AppCompatImageButton>(R.id.play_articulation)
        fun bind(data: DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {

                header_textview_recycler_item.text = data.text
                description_textview_recycler_item.text =
                    data.meanings?.get(0)?.translation?.translation
                transcription_textview_recycler_item.text =
                    "[" + data.meanings?.get(0)?.transcription + "]"

                set_favorite.apply {
                    if (data.inFavoriteList) {
                        setImageResource(R.drawable.baseline_favorite_24)
                    } else {
                        setImageResource(R.drawable.baseline_favorite_border_24)
                    }
                    setOnClickListener {
                        data.inFavoriteList = !data.inFavoriteList
                        if (data.inFavoriteList) {
                            setImageResource(R.drawable.baseline_favorite_24)
                        } else {
                            setImageResource(R.drawable.baseline_favorite_border_24)
                        }
                        putInFavoriteList(data, position, data.inFavoriteList)
                    }
                }
                play_articulation.setOnClickListener {
                    it?.apply {
                        isEnabled = false
                        postDelayed({ isEnabled = true }, 400)
                    }
                    data.meanings?.get(0)?.soundUrl?.let { sound_url ->
                        playArticulationClickListener(sound_url)
                    }
                }

                itemView.setOnClickListener { openInNewWindow(data) }
            }
        }
    }

    private fun putInFavoriteList(favoriteData: DataModel, position: Int, inFavoriteList: Boolean) {
        putInFavoriteListListener(favoriteData, position, inFavoriteList)
    }


    private fun openInNewWindow(listItemData: DataModel) {
        onListItemClickListener(listItemData)
    }
}
