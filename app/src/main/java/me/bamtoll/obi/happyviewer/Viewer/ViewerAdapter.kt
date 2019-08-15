package me.bamtoll.obi.happyviewer.Viewer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import me.bamtoll.obi.happyviewer.R

class ViewerAdapter(data: List<String>): RecyclerView.Adapter<ViewerAdapter.ViewHolder>() {

    var mData: List<String> = data

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_reader, p0, false))
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        // p0.numText.text = p1.plus(1).toString()
        p0.numText.visibility = View.GONE
        Picasso.get().load(mData[p1]).priority(Picasso.Priority.HIGH).transform(ResizeTransformation())
                /*.networkPolicy(NetworkPolicy.OFFLINE)
                .noFade().placeholder(R.drawable.ic_loop_black_24dp).into(
                        p0.pieceImage, object: Callback {
                            override fun onSuccess() {}

                            override fun onError(e: Exception?) {
                                Picasso.get()
                                        .load(mData[p1])
                                        .into(p0.pieceImage, object: Callback {
                                            override fun onSuccess() { }

                                            override fun onError(e: Exception?) {
                                                Log.v("Picasso", "Could not fetch image")
                                            }
                                        })
                            }

                        }
                )*/
                .placeholder(R.drawable.ic_loop_black_24dp)
                .into(p0.pieceImage)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var pieceImage: ImageView = itemView.findViewById(R.id.image_piece) as ImageView
        var numText: TextView = itemView.findViewById(R.id.text_num) as TextView
    }
}