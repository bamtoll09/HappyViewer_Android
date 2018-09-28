package me.bamtoll.obi.happyviewer

import android.app.PendingIntent.getActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import java.lang.Exception

class PieceAdapter(data: List<String>): RecyclerView.Adapter<PieceAdapter.ViewHolder>() {

    var mData: List<String> = data
    var mNum = 0

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        if (mNum == 0) {
            val builder = Picasso.Builder(p0.context)
            builder.downloader(OkHttp3Downloader(p0.context, Integer.MAX_VALUE.toLong()))
            val built = builder.build()
            built.setIndicatorsEnabled(true)
            built.isLoggingEnabled = true
            Picasso.setSingletonInstance(built)
            mNum = 1
        }

        return ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_piece, p0, false))
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.numText.text = p1.plus(1).toString()
        Picasso.get().load(mData[p1]).priority(Picasso.Priority.HIGH).transform(ResizeTransformation())
                .networkPolicy(NetworkPolicy.OFFLINE).noFade().placeholder(R.mipmap.ic_launcher).into(
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
                )
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var pieceImage: ImageView = itemView.findViewById(R.id.image_piece) as ImageView
        var numText: TextView = itemView.findViewById(R.id.text_num) as TextView
    }
}