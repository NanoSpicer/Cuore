package com.nanospicer.cuore

// From package com.nanospicer.nanoadapter


import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


abstract class IDiffableBindable : Diffable, IBindable

interface Diffable {
    fun areContentsTheSameAs(other: Diffable): Boolean = false
    fun isItemTheSameAs(other: Diffable): Boolean = false
}

open class MutableNanoAdapter(val currentDataSet: MutableList<NanoBindable<*>> = mutableListOf()) : NanoAdapter(currentDataSet) {

    fun updateDataset(newDataSet: List<NanoBindable<*>>) {
        val cb = object : DiffUtil.Callback(){
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                currentDataSet[oldItemPosition].isItemTheSameAs(newDataSet[newItemPosition])
            override fun getOldListSize(): Int = currentDataSet.size
            override fun getNewListSize(): Int = newDataSet.size
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                currentDataSet[oldItemPosition].areContentsTheSameAs(newDataSet[newItemPosition])
        }

        val result = DiffUtil.calculateDiff(cb, true)

        currentDataSet.apply {
            clear()
            addAll(newDataSet)
        }
        recalculateInflaters()

        result.dispatchUpdatesTo(this)
    }
}


open class NanoAdapter(val dataSet: List<IBindable>) : RecyclerView.Adapter<NanoDatabindingView>() {

    private var createdItems: Int = 0
    private val inflaterFunctions: MutableMap<Int, (LayoutInflater, ViewGroup) -> ViewDataBinding> = mutableMapOf()

    /**
     * The inflater functions map is a set that maps the item's viewType to their matching inflater functions.
     * We get all the item's distinct viewTypes and for those, map each viewType to it's inflating function
     */
    init { recalculateInflaters() }

    protected fun recalculateInflaters() {
        dataSet.distinctBy { it.viewType }.forEach { element -> inflaterFunctions[element.viewType] = element::onCreateView }
    }

    /**
     * Gets the item's viewType which is not the real one, but instead a unique hash code
     * of the class that will be holding the view
     */
    override fun getItemViewType(position: Int): Int = dataSet[position].viewType

    /** Creates a new viewHolder using the corresponding inflaterFunction referenced by its [viewType] */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NanoDatabindingView =
        with(LayoutInflater.from(parent.context)) { NanoDatabindingView(inflaterFunctions[viewType]!!.invoke(this, parent)) }
    // .also { Log.v("NanoAdapter", "Created ${++createdItems} views") }

    override fun getItemCount(): Int = dataSet.size

    /** Delegates the binding action to the item implementing that function */
    override fun onBindViewHolder(holder: NanoDatabindingView, position: Int) = dataSet[position].onBind(holder.binding)

}

class NanoDatabindingView(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)


interface IBindable {
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup): ViewDataBinding
    fun onBind(binding: ViewDataBinding)
    val viewType: Int
        get() = this::class.hashCode()

}

abstract class NanoBindable<V : ViewDataBinding> : IDiffableBindable() {
    override fun onBind(binding: ViewDataBinding)  = onBinding(binding as V)
    abstract fun onBinding(binding: V)
}

val <V: ViewDataBinding> V.resources: Resources
    get() = this.root.resources

val <V: ViewDataBinding> V.context: Context
    get() = this.root.context
