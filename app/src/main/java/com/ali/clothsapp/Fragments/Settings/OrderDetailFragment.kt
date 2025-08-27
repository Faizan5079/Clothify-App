package com.ali.clothsapp.Fragments.Settings


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ali.clothsapp.Adapters.BillingProductsAdapter
import com.ali.clothsapp.Data.Order.OrderStatus
import com.ali.clothsapp.Data.Order.getOrderStatus
import com.ali.clothsapp.R
import com.ali.clothsapp.databinding.FragmentOrderDetailBinding
import com.ali.clothsapp.databinding.FragmentOrdersBinding
import com.ali.clothsapp.Util.VerticalItemDecoration
import com.ali.clothsapp.Util.hideBottomNavigationView

class OrderDetailFragment : Fragment() {
    private lateinit var binding: FragmentOrderDetailBinding
    private val billingProductsAdapter by lazy { BillingProductsAdapter() }
    private val args by navArgs<OrderDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val order = args.order
        hideBottomNavigationView()

        setupOrderRv()

        binding.apply {

            tvOrderId.text = "Order #${order.orderId}"


            //stepView.setSteps(
              //  mutableListOf(
                //    OrderStatus.Ordered.status,
                  //  OrderStatus.Confirmed.status,
                    //OrderStatus.Shipped.status,
                    //OrderStatus.Delivered.status,
               // )
           // )

            val currentOrderState = when (getOrderStatus(order.orderStatus)) {
                is OrderStatus.Ordered -> 0
                is OrderStatus.Confirmed -> 1
                is OrderStatus.Shipped -> 2
                is OrderStatus.Delivered -> 3
                else -> 0
            }

            //stepView.go(currentOrderState, false)
            //if (currentOrderState == 3) {
              //  stepView.done(true)
           // }
            updateStepIndicator(currentOrderState)

            tvFullName.text = order.address.fullName
            tvAddress.text = "${order.address.street} ${order.address.city}"
            tvPhoneNumber.text = order.address.phone

            tvTotalPrice.text = "$ ${order.totalPrice}"

        }

        billingProductsAdapter.differ.submitList(order.products)
    }

    private fun setupOrderRv() {
        binding.rvProducts.apply {
            adapter = billingProductsAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            addItemDecoration(VerticalItemDecoration())
        }
    }
    private fun updateStepIndicator(currentStep: Int) {
        val steps = listOf(
            binding.linearStepView.getChildAt(0) as ViewGroup, // Ordered
            binding.linearStepView.getChildAt(2) as ViewGroup, // Confirmed
            binding.linearStepView.getChildAt(4) as ViewGroup, // Shipped
            binding.linearStepView.getChildAt(6) as ViewGroup  // Delivered
        )

        for ((index, stepLayout) in steps.withIndex()) {
            val circle = stepLayout.getChildAt(0)
            val text = stepLayout.getChildAt(1) as? TextView

            when {
                index < currentStep -> {
                    circle.setBackgroundResource(R.drawable.circle_done)
                    text?.setTextColor(resources.getColor(R.color.g_gray700, null))
                }
                index == currentStep -> {
                    circle.setBackgroundResource(R.drawable.circle_selected)
                    text?.setTextColor(resources.getColor(R.color.g_gray700, null))
                }
                else -> {
                    circle.setBackgroundResource(R.drawable.circle_pending)
                    text?.setTextColor(resources.getColor(R.color.g_gray700, null))
                }
            }
        }
    }
}