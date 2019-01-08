package by.marpod.cdekapp.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.marpod.cdekapp.R
import by.marpod.cdekapp.data.dto.Direction
import by.marpod.cdekapp.data.dto.Request
import by.marpod.cdekapp.data.dto.TransportationMethod
import by.marpod.cdekapp.ui.adapter.HandleRequestRecyclerViewAdapter.ViewHolder
import by.marpod.cdekapp.ui.adapter.HandleRequestRecyclerViewAdapter.ViewHolder.*
import by.marpod.cdekapp.util.extensions.inflate
import by.marpod.cdekapp.util.extensions.text
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_direction.*
import kotlinx.android.synthetic.main.list_item_handle_request_add.*
import kotlinx.android.synthetic.main.list_item_handle_request_footer.*
import kotlinx.android.synthetic.main.list_item_handle_request_header.*

class HandleRequestRecyclerViewAdapter(
        private val context: Context,
        private val request: Request
) : RecyclerView.Adapter<ViewHolder>() {

    companion object {
        private const val HEADER = 0
        private const val ITEM = 1
        private const val ADD = 2
        private const val FOOTER = 3
    }

    var directions: List<Direction> = emptyList()
        set(value) {
            field = value
            buildMergedList(directionsList = value)
        }

    var onAddAnotherClicked: () -> Unit = {}

    var onSaveDestinationClicked: () -> Unit = {}

    var onChooseDestinationClicked: (cityFrom: String) -> Unit = {}

    private val differ = AsyncListDiffer<Any>(this, DiffCallback)

    init {
        differ.submitList(buildMergedList())
    }

    private lateinit var holderAdd: AddDirectionViewHolder

    fun setCard(request: Request, direction: Direction) {
        holderAdd.setCard(request, direction)
    }

    override fun getItemCount() = differ.currentList.size

    override fun getItemViewType(position: Int) = when (differ.currentList[position]) {
        is Header -> HEADER
        is Direction -> ITEM
        is Add -> ADD
        is Footer -> FOOTER
        else -> throw IllegalStateException("Unknown view type at position $position")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = when (viewType) {
        HEADER -> HeaderViewHolder(parent.inflate(R.layout.list_item_handle_request_header))
        ITEM -> DirectionViewHolder(parent.inflate(R.layout.list_item_direction))
        ADD -> AddDirectionViewHolder(parent.inflate(R.layout.list_item_handle_request_add))
        FOOTER -> FooterViewHolder(parent.inflate(R.layout.list_item_handle_request_footer))
        else -> throw IllegalStateException("Unknown viewType $viewType")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]
        when (holder) {
            is HeaderViewHolder -> holder.bind(context, request, directions)
            is DirectionViewHolder -> holder.bind(request, item as Direction)
            is AddDirectionViewHolder -> {
                val lastDirection = differ.currentList[position - 1] as? Direction
                holder.bind(request, lastDirection) { onChooseDestinationClicked(it) }
            }
            is FooterViewHolder -> holder.bind {
                when (it.id) {
                    R.id.btn_add_another -> onAddAnotherClicked()
                    R.id.btn_save_direction -> onSaveDestinationClicked()
                }
            }
        }
    }

    private fun buildMergedList(directionsList: List<Direction> = directions): List<Any> {
        val merged = mutableListOf<Any>(Header)
        merged.addAll(directionsList)
        merged +=
                if (directionsList.isNotEmpty() && directionsList.last().hasCity(request.cityTo)) Footer
                else Add
        return merged
    }

    object Header

    object Add

    object Footer

    object DiffCallback : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return when {
                oldItem === Header && newItem === Header -> false
                oldItem === Add && newItem === Add -> true
                oldItem === Footer && newItem === Footer -> true
                oldItem is Direction && newItem is Direction -> oldItem.id == newItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return when {
                oldItem is Direction && newItem is Direction -> oldItem == newItem
                else -> true
            }
        }
    }

    sealed class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        class HeaderViewHolder(
                override val containerView: View
        ) : ViewHolder(containerView) {

            fun bind(context: Context, request: Request, directions: List<Direction>) {
                var time = 0
                var cost = 0
                for (item in directions) {
                    time += item.hours
                    cost += item.calculateCost(request.getSize())
                }
                expecting_time.text = context.getString(R.string.hours, time)
                total_cost.text = context.getString(R.string.byn, cost)
            }
        }

        class DirectionViewHolder(
                override val containerView: View
        ) : ViewHolder(containerView) {

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

                if (direction.hasCity(request.cityTo)) {
                    arrow.isGone = true
                }
            }
        }

        class AddDirectionViewHolder(
                override val containerView: View
        ) : ViewHolder(containerView) {

            fun bind(request: Request, lastDirection: Direction? = null, onClick: (cityFrom: String) -> Unit) {
                destination_from.text = request.cityFrom
                card.setOnClickListener { onClick(destination_from.text) }
            }

            fun setCard(request: Request, direction: Direction) {
                transfer_type.setImageResource(when (direction.method) {
                    TransportationMethod.PLANE -> R.drawable.ic_plane
                    TransportationMethod.AUTO -> R.drawable.ic_truck
                    TransportationMethod.TRAIN -> R.drawable.ic_train
                    TransportationMethod.SHIP -> R.drawable.ic_ship
                    else -> throw IllegalStateException("unknown transportation method: ${direction.method}")
                })
                card_city_from.text = direction.firstCity
                card_city_to.text = direction.secondCity
                card_delivery_time.text = containerView.context.getString(R.string.hours, direction.hours)
                card_cost.text = containerView.context.getString(R.string.byn, direction.calculateCost(request.getSize()))
            }
        }

        class FooterViewHolder(
                override val containerView: View
        ) : ViewHolder(containerView) {

            fun bind(listener: (View) -> Unit) {
                btn_save_direction.setOnClickListener(listener)
                btn_add_another.setOnClickListener(listener)
            }
        }
    }
}