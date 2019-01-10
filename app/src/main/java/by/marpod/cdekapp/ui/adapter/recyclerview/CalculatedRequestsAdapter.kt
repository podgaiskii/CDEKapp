package by.marpod.cdekapp.ui.adapter.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import by.marpod.cdekapp.R
import by.marpod.cdekapp.data.dto.Request
import by.marpod.cdekapp.ui.adapter.recyclerview.CalculatedRequestsAdapter.ViewHolder
import by.marpod.cdekapp.util.extensions.inflate
import kotlinx.android.synthetic.main.list_item_request.view.*

class CalculatedRequestsAdapter(items: List<Request> = emptyList(),
                                private val onClick: (request: Request) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    var items: List<Request> = items
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent.inflate(R.layout.list_item_request))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, onClick)
    }

    class ViewHolder(private val containerView: View) : RecyclerView.ViewHolder(containerView) {

        fun bind(request: Request, onClick: (request: Request) -> Unit) {
            containerView.apply {
                city_from.text = request.cityFrom
                city_to.text = request.cityTo
                tv_length.text = request.length.toString()
                tv_width.text = request.width.toString()
                tv_height.text = request.height.toString()
                username.isGone = true
                setOnClickListener { onClick(request) }
            }
        }
    }
}