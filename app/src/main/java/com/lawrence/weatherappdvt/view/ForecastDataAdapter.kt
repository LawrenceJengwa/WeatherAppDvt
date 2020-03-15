package com.lawrence.weatherappdvt.view



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lawrence.weatherappdvt.R
import com.lawrence.weatherappdvt.databinding.SavedForecastDataListBinding
import com.lawrence.weatherappdvt.viewmodel.ForecastDataSummaryItem


class ForecastDataAdapter(val onItemSelected: (item: ForecastDataSummaryItem) -> Unit)
    : RecyclerView.Adapter<ForecastDataAdapter.ForecastDataViewHolder>() {

    private val focastDataSummaries = mutableListOf<ForecastDataSummaryItem>()

    fun updateList(updates: List<ForecastDataSummaryItem>) {
        focastDataSummaries.clear()
        focastDataSummaries.addAll(updates)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastDataViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<SavedForecastDataListBinding>(
            inflater, R.layout.saved_forecast_data_list, parent, false)

        return ForecastDataViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return focastDataSummaries.size
    }

    override fun onBindViewHolder(holder: ForecastDataViewHolder, position: Int) {
        holder.bind(focastDataSummaries[position])
    }

    inner class ForecastDataViewHolder(val binding: SavedForecastDataListBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ForecastDataSummaryItem) {
            binding.forecast = item
            binding.root.setOnClickListener { onItemSelected(item) }
            binding.executePendingBindings()
        }

    }

}