package by.marpod.cdekapp.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.marpod.cdekapp.R
import by.marpod.cdekapp.data.dto.Route
import by.marpod.cdekapp.util.extensions.inflate
import kotlinx.android.synthetic.main.list_item_route.view.*

class AvailableRoutesRecyclerViewAdapter(private val items: List<Route>) : RecyclerView.Adapter<AvailableRoutesRecyclerViewAdapter.ViewHolder>() {

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent.inflate(R.layout.list_item_route))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    class ViewHolder(private val containerView: View) : RecyclerView.ViewHolder(containerView) {

        fun bind(route: Route) {
            containerView.apply {
                tv_route.text = route.route.joinToString(" > ") { it.name }
                tv_delivery_time.text = route.days.toString() + " days"
                tv_cost.text = route.cost.toString() + " BYN"
            }
        }
    }
}