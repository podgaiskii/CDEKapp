package by.marpod.cdekapp.ui.adapter.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import by.marpod.cdekapp.R
import by.marpod.cdekapp.data.dto.Direction
import by.marpod.cdekapp.data.dto.Request
import by.marpod.cdekapp.data.dto.TransportationMethod
import by.marpod.cdekapp.util.extensions.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_direction.*

class DirectionsAdapter(private val directions: List<Direction>,
                        private val request: Request) : RecyclerView.Adapter<DirectionsAdapter.ViewHolder>() {

    var onDirectionClicked: (Direction) -> Unit = {}

    override fun getItemCount() = directions.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.list_item_direction))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = directions[position]
        holder.apply {
            bind(request, item)
            containerView.setOnClickListener { onDirectionClicked(item) }
        }
    }

    class ViewHolder(
            override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(request: Request, direction: Direction) {
            transfer_type_pic.setImageResource(when (direction.method) {
                TransportationMethod.PLANE -> R.drawable.ic_plane
                TransportationMethod.AUTO -> R.drawable.ic_truck
                TransportationMethod.TRAIN -> R.drawable.ic_train
                TransportationMethod.SHIP -> R.drawable.ic_ship
                else -> throw IllegalStateException("unknown transportation method: ${direction.method}")
            })
            city_from.text = direction.firstCity
            city_to.text = direction.secondCity
            expecting_delivery_time.text = containerView.context.getString(R.string.hours, direction.hours)
            cost.text = containerView.context.getString(R.string.byn, direction.calculateCost(request.getSize()))
            arrow.isGone = true
            divider.isVisible = true
        }
    }
}