package com.antonicastejon.cryptodata.presentation.common

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.antonicastejon.cryptodata.R
import com.antonicastejon.cryptodata.common.formatTo
import com.antonicastejon.cryptodata.domain.UserViewModel
import com.antonicastejon.cryptodata.domain.emptyUserViewModel
import com.antonicastejon.cryptodata.presentation.widgets.paginatedRecyclerView.PaginationAdapter
import kotlinx.android.synthetic.main.crypto_list_item.view.*

/**
 * Created by Antoni Castejón on 07/01/2018.
 */

private const val DECIMALS_FIAT = 4
private const val DECIMALS_BTC = 7
private const val DECIMALS_CHANGE = 2

class UserListRecyclerAdapter : PaginationAdapter<UserViewModel>() {
    override fun addLoadingViewFooter() {
        addLoadingViewFooter(emptyUserViewModel)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is UserViewHolder) holder.bind(dataList[position])
    }

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.crypto_list_item, parent, false)
        return UserViewHolder(view)
    }

    fun updateData(newData: List<UserViewModel>) {
        val fromIndex = dataList.size
        dataList = newData.toMutableList()
        notifyItemRangeInserted(fromIndex, newData.size)
    }
}

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val resources by lazy { itemView.context.resources }

    fun bind(item: UserViewModel) {
        itemView.apply {
            tvPosition.text = item.rank.toString()
            tvSymbol.text = item.symbol
            tvPrice.text = bindPrice(item)
            tvChange24h.text = bindChangeText(item)
            tvChange24h.setTextColor(bindChangeColor(item))
        }
    }

    private fun bindPrice(item: UserViewModel) =
            if (item.isBtc()) resources.getString(R.string.price_btc, item.priceFiat.formatTo(DECIMALS_FIAT))
            else resources.getString(R.string.price_alts, item.priceFiat.formatTo(DECIMALS_FIAT), item.priceBtc.formatTo(DECIMALS_BTC))

    private fun bindChangeColor(item: UserViewModel) =
            ContextCompat.getColor(itemView.context, if (item.change > 0) R.color.change_positive else R.color.change_negative)

    private fun bindChangeText(item: UserViewModel) =
            resources.getString(R.string.change_percent, item.change.formatTo(DECIMALS_CHANGE))
}
